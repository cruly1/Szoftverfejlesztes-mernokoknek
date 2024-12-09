import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "./EventList.css";

function EventList() {
  const [events, setEvents] = useState([]);
  const [currentEvents, setCurrentEvents] = useState([]);
  const [upcomingEvents, setUpcomingEvents] = useState([]);
  const [pastEvents, setPastEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [joiningEvent, setJoiningEvent] = useState(false);
  const [participatingEvents, setParticipatingEvents] = useState([]);
  const [teamName, setTeamName] = useState(null);
  const [teamEvents, setTeamEvents] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const nickName = localStorage.getItem("nickname");

    if (!token || !nickName) {
      setError("Unauthorized access. Please log in.");
      setLoading(false);
      return;
    }

    // Fetch all events
    axios
      .get("http://localhost:8080/api/events/getAllEvents", {
        headers: { Authorization: `Bearer ${token}` },
        withCredentials: true,
      })
      .then((response) => {
        const currentDate = new Date();

        // Separate events into categories
        const past = response.data.filter(
          (event) => new Date(event.eventEndDate) < currentDate
        );
        const current = response.data.filter(
          (event) =>
            new Date(event.eventStartDate) <= currentDate &&
            new Date(event.eventEndDate) >= currentDate
        );
        const upcoming = response.data.filter(
          (event) => new Date(event.eventStartDate) > currentDate
        );

        setPastEvents(past);
        setCurrentEvents(current);
        setUpcomingEvents(upcoming);
      })
      .catch((err) => {
        console.error(err);
        setError("Failed to fetch events.");
      });

    // Fetch player's details
    axios
      .get(
        `http://localhost:8080/api/players/getByNickName/search?nickName=${nickName}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      )
      .then((response) => {
        const playerData = response.data;
        const playerEvents = playerData.events.map((event) => ({
          eventName: event.eventName,
          type: "solo",
        }));
        setParticipatingEvents(playerEvents);
        setTeamName(playerData.teamName || null);

        // Fetch team's events if player belongs to a team
        if (playerData.teamName) {
          axios
            .get(
              `http://localhost:8080/api/teams/search?teamName=${playerData.teamName}`,
              {
                headers: { Authorization: `Bearer ${token}` },
              }
            )
            .then((teamResponse) => {
              const teamEventData = teamResponse.data.events.map((event) => ({
                eventName: event.eventName,
                type: "team",
              }));
              setTeamEvents(teamEventData);
            })
            .catch((err) => console.error("Failed to fetch team events:", err));
        }
      })
      .catch((err) => {
        console.error("Failed to fetch player's data:", err);
        setError("Failed to fetch player's data.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handleJoinEventAsPlayer = async (eventName) => {
    const nickName = localStorage.getItem("nickname");
    const token = localStorage.getItem("token");

    if (!nickName || !token) {
      alert("Player not logged in or unauthorized access.");
      return;
    }

    setJoiningEvent(true);

    try {
      await axios.put(
        `http://localhost:8080/api/players/addToEvent/search?nickName=${nickName}&eventName=${eventName}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setParticipatingEvents([
        ...participatingEvents,
        { eventName, type: "solo" },
      ]);
    } catch (err) {
      console.error("Error joining event as player:", err);
      alert("Failed to join the event. Please try again.");
    } finally {
      setJoiningEvent(false);
    }
  };

  const handleJoinEventAsTeam = async (eventName) => {
    const token = localStorage.getItem("token");

    if (!teamName) {
      alert("You don't belong to a team. Unable to join the event as a team.");
      return;
    }

    setJoiningEvent(true);

    try {
      await axios.put(
        `http://localhost:8080/api/teams/addToEvent/search?teamName=${teamName}&eventName=${eventName}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      setTeamEvents([...teamEvents, { eventName, type: "team" }]);
    } catch (err) {
      console.error("Error joining event as team:", err);
      alert("Failed to join the event as a team. Please try again.");
    } finally {
      setJoiningEvent(false);
    }
  };
  const handleLeaveEvent = async (eventName) => {
    const nickName = localStorage.getItem("nickname");
    const token = localStorage.getItem("token");
  
    if (!nickName || !token) {
      alert("Unauthorized access. Please log in.");
      return;
    }
  
    setJoiningEvent(true);
  
    try {
      // Call the leave event API
      await axios.put(
        `http://localhost:8080/api/players/leaveEvent/search?nickName=${nickName}&eventName=${eventName}`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
  
      // Update the UI after successfully leaving the event
      setParticipatingEvents((prev) => prev.filter((event) => event.eventName !== eventName));
      setTeamEvents((prev) => prev.filter((event) => event.eventName !== eventName));
  
      alert("You have successfully left the event.");
    } catch (err) {
      console.error("Error leaving event:", err);
      alert("Failed to leave the event. Please try again.");
    } finally {
      setJoiningEvent(false);
    }
  };
  

  if (loading) return <div>Loading events...</div>;
  if (error) return <div>{error}</div>;

  const allParticipatingEvents = [...participatingEvents, ...teamEvents];

  return (
    <div className="event-list-container">
      <div className="upcoming-events">
        <h2>Upcoming Events</h2>
        {upcomingEvents.length > 0 ? (
          upcomingEvents.map((event) => {
            const participation = allParticipatingEvents.find(
              (e) => e.eventName === event.eventName
            );

            return (
              <div key={event.eventName} className="event-item">
                <Link to={`/events/${event.eventName}`} className="event-link">
                  <h2>{event.eventName}</h2>
                  <p>
                    <strong>Start Date:</strong> {event.eventStartDate}
                  </p>
                  <p>
                    <strong>End Date:</strong> {event.eventEndDate}
                  </p>
                </Link>
                <div className="event-actions">
                  {participation ? (
                    <div className="participation-actions">
                      <span className="already-joined-text">
                        Already participating as{" "}
                        {participation.type === "team"
                          ? `Team (${teamName})`
                          : "Solo"}
                      </span>
                      <button
                        className="leave-event-button"
                        onClick={() => handleLeaveEvent(event.eventName)}
                        disabled={joiningEvent}
                      >
                        {joiningEvent ? "Leaving..." : "Leave Event"}
                      </button>
                    </div>
                  ) : (
                    <>
                      <button
                        className="join-event-player-button"
                        onClick={() => handleJoinEventAsPlayer(event.eventName)}
                        disabled={joiningEvent}
                      >
                        {joiningEvent ? "Joining..." : "Join as Player"}
                      </button>
                      {teamName && (
                        <button
                          className="join-event-team-button"
                          onClick={() => handleJoinEventAsTeam(event.eventName)}
                          disabled={joiningEvent}
                        >
                          {joiningEvent ? "Joining..." : "Join as Team"}
                        </button>
                      )}
                    </>
                  )}
                </div>
              </div>
            );
          })
        ) : (
          <p>No upcoming events available.</p>
        )}
      </div>

      <div className="current-events">
        <h2>Current Events</h2>
        {currentEvents.length > 0 ? (
          currentEvents.map((event) => (
            <div key={event.eventName} className="event-item">
              <Link to={`/events/${event.eventName}`} className="event-link">
                <h2>{event.eventName}</h2>
                <p>
                  <strong>Start Date:</strong> {event.eventStartDate}
                </p>
                <p>
                  <strong>End Date:</strong> {event.eventEndDate}
                </p>
              </Link>
              <span className="already-joined-text">Currently Running</span>
            </div>
          ))
        ) : (
          <p>No current events available.</p>
        )}
      </div>

      <div className="past-events">
        <h2>Past Events</h2>
        {pastEvents.length > 0 ? (
          pastEvents.map((event) => (
            <div key={event.eventName} className="event-item">
              <Link to={`/events/${event.eventName}`} className="event-link">
                <h2>{event.eventName}</h2>
                <p>
                  <strong>Start Date:</strong> {event.eventStartDate}
                </p>
                <p>
                  <strong>End Date:</strong> {event.eventEndDate}
                </p>
              </Link>
            </div>
          ))
        ) : (
          <p>No past events available.</p>
        )}
      </div>
    </div>
  );
}

export default EventList;
