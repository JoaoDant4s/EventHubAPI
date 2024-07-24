import { useLocation, useNavigate } from "react-router-dom";
import { faList, faUser, faCreditCard, faTicket, faMapLocationDot, IconDefinition } from "@fortawesome/free-solid-svg-icons";
import NavBar from "../../components/NavBar";
import Container from "../../components/Container";
import Main from "../../components/Main";
import { ComponentProps, ReactNode, useEffect, useState } from "react";
import { apiGetByEmail } from "../../api/services/user";
import { UserDto } from "../../api/services/user/user";
import Profile from "./profile/Profile";
import NextEvents from "./nextEvents/NextEvents";
import { NavItem, Role } from "../../../main";


export type DashboardProps = ComponentProps<'div'> & {
  routes:Array<NavItem>
}

export default function Dashboard({routes}:DashboardProps) {
  const location = useLocation();
  const navigate = useNavigate();
  const [user,setUser] = useState<UserDto>();
  const [role, setRole] = useState<Role>("Participante");
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
      setRole("admin");
    } else if(localStorage.getItem("ROLE_PROMOTER") == "ROLE_PROMOTER") {
      setRole("promoter");
    } else if(localStorage.getItem("ROLE_PROMOTER") == "ROLE_PROMOTER") {
      setRole("attraction");
    } else {
      setRole("participant");
    }
  }

  const getRoleName=()=>{
    if(role==="admin"){
      return "Administrador"
    } else if(role==="promoter"){
      return "Promotor"
    } else if(role==="attraction"){
      return "Atração"
    } else {
      return "Participante"
    }
  }

  const setNavItemList=()=>{
    setCurrentRoute(routes.find((item)=>item.path === location.pathname));
    setNavItems(routes.filter((item=>item.permission.find((p)=>p === role && item.navbar === true))));
  }

  useEffect (()=>{
    setUserData();
    setUserRole();
  });
  
  useEffect (()=>{
    setNavItemList();
  }, [currentRoute, location]);

  return (
    <>
        <Container>
          { user && navItems && (
            <NavBar name={user.name} role={getRoleName()} navItems={navItems} routes={routes} />
          )}
          <Main>
            {currentRoute && currentRoute.component}
          </Main>
        </Container>
    </>
  )
}
