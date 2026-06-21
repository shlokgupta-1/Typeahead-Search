function SuggestionsDropdown({
  suggestions,
  selectedIndex,
  onSelect
}) {

  if (
    !suggestions ||
    suggestions.length === 0
  ) {

    return null;
  }

  return (

    <div
      className="
        list-group
        mt-2
      "
    >

      {suggestions.map(
        (item, index) => (

          <button
            key={item.query}
            className={
              selectedIndex === index
                ? "list-group-item list-group-item-action active"
                : "list-group-item list-group-item-action"
            }
            onClick={() =>
              onSelect(item.query)
            }
          >

            <div>

              {item.query}

            </div>

            <small>

              Searches:

              {" "}

              {item.searchCount}

            </small>

          </button>

        )
      )}

    </div>

  );
}

export default SuggestionsDropdown;