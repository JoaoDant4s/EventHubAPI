import { useNavigate } from "react-router-dom";
import Title from "../../../components/Title";

export default function NextEvents() {
  const navigate = useNavigate();

  return (
    <>
        <Title>Proximos Eventos</Title>
    </>
  )
}
