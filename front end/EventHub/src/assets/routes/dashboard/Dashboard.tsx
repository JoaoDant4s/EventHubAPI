import { useLocation, useNavigate } from "react-router-dom";
import { faList, faUser, faCreditCard, faTicket, faMapLocationDot, IconDefinition } from "@fortawesome/free-solid-svg-icons";
import NavBar, { NavItem } from "../../components/NavBar";
import Container from "../../components/Container";
import Main from "../../components/Main";
import { useEffect, useState } from "react";
import { apiGetByEmail } from "../../api/services/user";
import { UserDto } from "../../api/services/user/user";
import Profile from "./profile/Profile";
import NextEvents from "./nextEvents/NextEvents";

export default function Dashboard() {
  const location = useLocation();
  const navigate = useNavigate();
  const [user,setUser] = useState<UserDto>();
  const [role, setRole] = useState<String>("Participante");
  const [navItems, setNavItems] = useState<Array<NavItem>>();
  const [currentRoute, setCurrentRoute] = useState<NavItem>();

  const setUserData = async () =>{
    const email = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(email)
    .then((response)=>{
      const data = response?.data;
      setUser(data);
    });
  }

  const setUserRole=()=>{
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

  const setNavItemList=()=>{
    const navItemList = new Array<NavItem>;
    if(localStorage.getItem("ROLE_USER") === "ROLE_USER"){
        navItemList.push({text:"Dashboard", icon:faList, path:"/dashboard", component: <NextEvents />});
        navItemList.push({text:"Perfil", icon:faUser, path:"/dashboard/profile", component: <Profile/> });
        navItemList.push({text:"Meus ingressos", icon:faTicket, path:"/dashboard/myTickets", component: <NextEvents/> });
        navItemList.push({text:"Cartão de crédito", icon:faCreditCard, path:"/dashboard/creditCard", component: <NextEvents/> });  
    }
    if(localStorage.getItem("ROLE_ADMIN") === "ROLE_ADMIN"){
        navItemList.push({text:"Usuário", icon:faList, path:"/admin/users", component: <NextEvents/>});
        navItemList.push({text:"Eventos", icon:faList, path:"/admin/events", component: <NextEvents/>});
    }
    if(localStorage.getItem("ROLE_PROMOTER") === "ROLE_PROMOTER"){
        navItemList.push({text:"Meus eventos", icon:faList, path:"/dashboard/myEvents", component: <NextEvents/>});
        navItemList.push({text:"Perfil", icon:faUser, path:"/dashboard/profile", component: <NextEvents/>});
    }
    if(localStorage.getItem("ROLE_ATTRACTION") === "ROLE_ATTRACTION"){
        navItemList.push({text:"Eventos participados", icon:faMapLocationDot,path:"/dashboard/lastEvents", component: <NextEvents/>});
    }

    const currentItem = navItemList.find((item)=>item.path === location.pathname);
    if(currentItem !== null) setCurrentRoute(currentItem);

    setNavItems(navItemList);
  }

  useEffect (()=>{
    setUserData();
    setUserRole();
  }, []);

  useEffect(()=>{
    setNavItemList();
  }, [currentRoute]);

  return (
    <>
        <Container>
          { user && (
            <NavBar name={user.name} role={role.toString()} items={navItems} />
          )}
          <Main>
            {currentRoute && currentRoute.component}
          </Main>
        </Container>
    </>
  )
}
