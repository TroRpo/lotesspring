import { useEffect, useState } from "react"
import "./index.css"

const BASE = "http://localhost:8080/api"

interface Lote {
  idLote: number
  referencia: string
  municipio: string
  areaM2: number
  precio: number
  estado: string
  imagenUrl?: string
  descripcion?: string
}

function App() {
  const [lotes, setLotes] = useState<Lote[]>([])
  const [search, setSearch] = useState("")
  const [estadoFiltro, setEstadoFiltro] = useState("")

  useEffect(() => {
    fetch(`${BASE}/lotes`)
      .then(res => res.json())
      .then(data => setLotes(data))
  }, [])

  const lotesFiltrados = lotes.filter(l =>
    (l.referencia.toLowerCase().includes(search.toLowerCase()) ||
      l.municipio.toLowerCase().includes(search.toLowerCase())) &&
    (estadoFiltro ? l.estado === estadoFiltro : true)
  )

  return (
    <div className="container">
      <h1 className="title">ATLAS Inmobiliaria</h1>

      <div className="filters">
        <input
          type="text"
          placeholder="Buscar por referencia o municipio..."
          value={search}
          onChange={e => setSearch(e.target.value)}
        />

        <select
          value={estadoFiltro}
          onChange={e => setEstadoFiltro(e.target.value)}
        >
          <option value="">Todos los estados</option>
          <option value="Disponible">Disponible</option>
          <option value="Vendido">Vendido</option>
          <option value="Reservado">Reservado</option>
        </select>
      </div>

      <div className="cards">
        {lotesFiltrados.map(l => (
          <div key={l.idLote} className="card">
            <img
              src={`/images/lote${l.idLote}.jpg`}
              alt="propiedad"
            />
            <div className="card-body">
              <h3>{l.referencia}</h3>
              <p className="municipio">{l.municipio}</p>
              <p>{l.descripcion || "Sin descripción disponible."}</p>
              <p><strong>Área:</strong> {l.areaM2} m²</p>
              <p className="precio">
                ${l.precio?.toLocaleString()}
              </p>
              <span className={`badge ${l.estado.toLowerCase()}`}>
                {l.estado}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default App