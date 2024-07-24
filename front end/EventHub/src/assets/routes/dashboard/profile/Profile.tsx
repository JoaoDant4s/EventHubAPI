import { useNavigate } from "react-router-dom";
import Title from "../../../components/Title";

export default function Profile() {
  const navigate = useNavigate();

  return (
    <>
        <Title>Perfil</Title>
    </>
  )
}
