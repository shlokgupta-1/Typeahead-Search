function TrendingSection({ trending }) {

    const medal = [
                    "🥇",
                    "🥈",
                    "🥉"
                    ];

  return (

    <div className="glass-card mt-5">

      <div className="p-4 border-bottom">

        <h4 className="mb-0"
            style={{
                color: "#ffffff"
            }}
            >
          📈 Trending Searches
        </h4>

      </div>

      <div className="p-4">

        {trending.map((item, index) => (

          <div
            key={item.id}
            className="
                trending-item
                d-flex
                justify-content-between
                align-items-center
                py-3
                border-bottom
            "
            >

            <div
                style={{
                color: "#ffffff",
                fontWeight: "600"
                }}
            >

                <span
                style={{
                    fontSize: "1.2rem",
                    marginRight: "10px"
                }}
                >
                {medal[index] || `#${index + 1}`}
                </span>

                {item.queryText}

            </div>

            <div
                style={{
                    textAlign: "right"
                }}
                >

                <div
                    style={{
                    color: "#60a5fa",
                    fontWeight: "700"
                    }}
                >
                    🔥 {item.searchCount}
                </div>

                <small
                    style={{
                    color: "#94a3b8"
                    }}
                >
                    Score {item.trendingScore?.toFixed(2)}
                </small>

                </div>

            </div>

                    ))}

                </div>

                </div>
  );
}

export default TrendingSection;