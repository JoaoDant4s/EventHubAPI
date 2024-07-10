import { useNavigate } from "react-router-dom"

export default function Dashboard() {
  const navigate = useNavigate();

  const logout=()=>{
    localStorage.removeItem("token");
    navigate("/");
  }

  return (
    
    <h1 className="font-bold underline">
      Hello world! Dashboard
      <br /><br />
      <button onClick={()=>logout()}>
        Sair
      </button>

    </h1>
  )
}
