function SystemStatus() {

  return (

    <div className="glass-card mt-5 p-4">

      <h4>
        ⚙️ System Status
      </h4>

      <div className="mt-3">

        <p style={{ color: "#ffffff" }}> 
            🟢 PostgreSQL Connected </p>

        <p style={{ color: "#ffffff" }}>🟢 Redis Connected</p>

        <p style={{ color: "#ffffff" }}>🟢 Trie Loaded</p>

        <p style={{ color: "#ffffff" }}>🟢 Batch Writer Active</p>

        <p style={{ color: "#ffffff" }}>🟢 Cache Nodes Active (3)</p>

      </div>

    </div>

  );
}

export default SystemStatus;