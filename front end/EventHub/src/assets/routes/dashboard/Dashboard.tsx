import { useNavigate } from "react-router-dom"
import NavBar from "../../components/NavBar";
import NavButton from "../../components/NavButton";
import { faList } from "@fortawesome/free-solid-svg-icons";
import Container from "../../components/Container";
import Main from "../../components/Main";
import Title from "../../components/Title";

export default function Dashboard() {
  const navigate = useNavigate();

  return (
    <>
      <Container>
        <NavBar>
          <NavButton icon={faList} color='default' >Dashboard</NavButton>
          <NavButton icon={faList} color='transparency'>Perfil</NavButton>
          <NavButton icon={faList} color='transparency'>Meus ingressos</NavButton>
          <NavButton icon={faList} color='transparency'>Cartão de crédito</NavButton>
        </NavBar>
        <Main>
          <Title>Próximos eventos</Title>
        </Main>
      </Container>
    </>
  )
}
