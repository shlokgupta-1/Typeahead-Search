import { useState } from "react";
import api from "../services/api";

function CacheDebug() {

  const [prefix, setPrefix] = useState("");
  const [result, setResult] = useState(null);

  async function checkNode() {

    if (!prefix.trim()) return;

    try {

      const response =
        await api.get(
          `/cache/debug?prefix=${prefix}`
        );

      setResult(response.data);

    } catch (err) {

      console.error(err);
    }
  }

  return (

    <div className="glass-card mt-5 p-4">

      <h4>
        🔄 Cache Debug
      </h4>

      <div className="input-group mt-3">

        <input
          className="form-control"
          placeholder="Enter Prefix"
          value={prefix}
          onChange={(e) =>
            setPrefix(e.target.value)
          }
        />

        <button
          className="btn btn-primary"
          onClick={checkNode}
        >
          Check
        </button>

      </div>

      {result && (

  <div className="mt-4">

    <div className="glass-card p-3">

      <p>
        🔑 Prefix:
        <strong>
          {" "}
          {result.prefix}
        </strong>
      </p>

      <p>
        🖥 Node:
        <strong>
          {" "}
          {result.node}
        </strong>
      </p>

      <p>
        {result.cacheHit
          ? "✅ Cache Hit"
          : "❌ Cache Miss"}
      </p>

    </div>

  </div>

)}

    </div>

  );
}

export default CacheDebug;