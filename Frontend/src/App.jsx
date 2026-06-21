import { useEffect, useState } from "react";
import SearchBar from "./components/SearchBar";
import TrendingSection from "./components/TrendingSection";
import CacheDebug from "./components/CacheDebug";
import SystemStatus from "./components/SystemStatus";
import api from "./services/api";
import "./App.css";

function App() {

const [trending, setTrending] = useState([]);

const [metrics, setMetrics] = useState({
cacheHits: 0,
cacheMisses: 0,
hitRate: 0
});

useEffect(() => {
loadTrending();
loadMetrics();

const interval = setInterval(() => {
  loadMetrics();
  loadTrending();
}, 3000);

return () => clearInterval(interval);

}, []);

async function loadTrending() {
try {
const response = await api.get("/trending");
setTrending(response.data);
} catch (err) {
console.error(err);
}
}

async function loadMetrics() {
try {
const response = await api.get("/metrics");
setMetrics(response.data);
} catch (err) {
console.error(err);
}
}

return (

  <div className="container py-5">

<div className="text-center mb-5">

  <h1 className="hero-title mb-3">
    🔍 Distributed Search Typeahead Engine
  </h1>

  <p
    className="lead"
    style={{
      color: "#cbd5e1"
    }}
  >
    Production-Scale Autocomplete System using Trie, Redis Cache, Consistent Hashing and Batch Processing
  </p>

</div>

<SearchBar />

<div className="row g-4 mt-4">

  <div className="col-md-4">

    <div className="glass-card metric-card p-4 h-100">

      <div className="text-center">

        <h6 className="text-uppercase text-secondary mb-3">
          Cache Hits
        </h6>

        <h1 style={{ color: "#60a5fa" }}>
          {metrics.cacheHits}
        </h1>

      </div>

    </div>

  </div>

  <div className="col-md-4">

    <div className="glass-card metric-card p-4 h-100">

      <div className="text-center">

        <h6 className="text-uppercase text-secondary mb-3">
          Cache Misses
        </h6>

        <h1 style={{ color: "#f59e0b" }}>
          {metrics.cacheMisses}
        </h1>

      </div>

    </div>

  </div>

  <div className="col-md-4">

    <div className="glass-card metric-card p-4 h-100">

      <div className="text-center">

        <h6 className="text-uppercase text-secondary mb-3">
          Hit Rate
        </h6>

        <h1 style={{ color: "#22c55e" }}>
          {(metrics.hitRate || 0).toFixed(1)}%
        </h1>

      </div>

    </div>

  </div>

</div>

<div className="mt-5">

  <div className="glass-card mt-5 p-4">

  <h4 className="mb-3">
    🔄 Consistent Hash Ring
  </h4>

  <p
    style={{
      color: "#cbd5e1"
    }}
  >
    Distributed cache nodes used
    for routing prefix keys.
  </p>

  <div className="d-flex gap-3 mt-3">

    <span
      className="badge fs-6"
      style={{
        background:
          "linear-gradient(90deg,#3b82f6,#60a5fa)"
      }}
    >
      Node A
    </span>

    <span
      className="badge fs-6"
      style={{
        background:
          "linear-gradient(90deg,#10b981,#34d399)"
      }}
    >
      Node B
    </span>

    <span
      className="badge fs-6"
      style={{
        background:
          "linear-gradient(90deg,#f59e0b,#fbbf24)"
      }}
    >
      Node C
    </span>

  </div>

</div>

<CacheDebug />

<SystemStatus />

  <TrendingSection
    trending={trending}
  />

  <div
    className="text-center mt-5"
    style={{
      color: "#94a3b8",
      fontSize: "0.9rem"
    }}
  >

    Built with React • Spring Boot • Redis • PostgreSQL

  </div>

</div>


  </div>

);

}

export default App;