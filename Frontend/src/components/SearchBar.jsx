import { useEffect, useState } from "react";
import api from "../services/api";
import useDebounce from "../hooks/useDebounce";
import SuggestionsDropdown from "./SuggestionsDropdown";

function SearchBar() {

  const [query, setQuery] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const [selectedIndex, setSelectedIndex] = useState(-1);
  const [message, setMessage] = useState("");

  const debouncedQuery = useDebounce(query, 300);

  useEffect(() => {

    if (!debouncedQuery.trim()) {
      setSuggestions([]);
      return;
    }

    fetchSuggestions();

  }, [debouncedQuery]);

  async function fetchSuggestions() {

    try {

      const response =
        await api.get(`/suggest?q=${debouncedQuery}`);

      setSuggestions(response.data);

    } catch (error) {
      console.error(error);
    }
  }

  async function performSearch(value) {

    try {

      const response =
        await api.post("/search", {
          query: value
        });

      setMessage(response.data.message);

    } catch (error) {
      console.error(error);
    }
  }

  function handleKeyDown(event) {

    if (event.key === "ArrowDown") {

      setSelectedIndex(prev =>
        Math.min(prev + 1, suggestions.length - 1)
      );

    } else if (event.key === "ArrowUp") {

      setSelectedIndex(prev =>
        Math.max(prev - 1, 0)
      );

    } else if (event.key === "Enter") {

      if (
        selectedIndex >= 0 &&
        suggestions[selectedIndex]
      ) {

        const selected =
          suggestions[selectedIndex].query;

        setQuery(selected);
        performSearch(selected);

      } else {

        performSearch(query);
      }
    }
  }

  return (

    <div className="glass-card p-4">

      <div className="input-group">

        <input
          className="form-control form-control-lg"
          placeholder="Search products, queries, topics..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onKeyDown={handleKeyDown}
        />

        <button
          className="btn search-btn text-white"
          onClick={() => performSearch(query)}
        >
          Search
        </button>

      </div>

      <SuggestionsDropdown
        suggestions={suggestions}
        selectedIndex={selectedIndex}
        onSelect={(value) => {
          setQuery(value);
          performSearch(value);
        }}
      />

      {message && (
        <div className="alert alert-success mt-3">
          {message}
        </div>
      )}

    </div>
  );
}

export default SearchBar;