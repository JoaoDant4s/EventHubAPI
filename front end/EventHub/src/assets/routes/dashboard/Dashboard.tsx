import { useNavigate } from "react-router-dom"
import NavBar from "../../components/NavBar";
import Container from "../../components/Container";
import Main from "../../components/Main";
import Title from "../../components/Title";
import { useEffect, useState } from "react";
import { apiGetByEmail } from "../../api/services/user";
import { UserDto } from "../../api/services/user/user";

export default function Dashboard() {
  const navigate = useNavigate();
  const [user,setUser] = useState<UserDto>();
  const [role, setRole] = useState<String>("Participante");

  const getData = async () =>{
    const email = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(email)
    .then((response)=>{
      const data = response?.data;
      setUser(data);
    });
  }

  const getRole=()=>{
    if(localStorage.getItem("ROLE_ADMIN") == "ROLE_ADMIN") {
      setRole("Administrador");
    } else if(localStorage.getItem("ROLE_PROMOTER") == "ROLE_PROMOTER") {
      setRole("Promotor");
    } else if(localStorage.getItem("ROLE_PROMOTER") == "ROLE_PROMOTER") {
      setRole("Atração");
    } else {
      setRole("Participante");
    }
  }

  useEffect (()=>{
    getData();
    getRole();
  }, [])

  return (
    <>
        <Container>
          { user && (
            <NavBar name={user.name} role={role.toString()} />
          )}
          <Main>
            <Title>Próximos eventos</Title>
          </Main>
        </Container>
    </>
  )
}
