import { useLocation, useNavigate } from "react-router-dom";
import NavBar from "../../components/NavBar";
import Container from "../../components/Container";
import Main from "../../components/Main";
import { ComponentProps, ReactNode, useEffect, useState } from "react";
import { apiGetByEmail } from "../../api/services/user";
import { UserDto } from "../../api/services/user/user";
import { NavItem, Role } from "../../../main";
import { getAlert, Status } from "../../components/Alert";


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

  //ALERT STATES
  const [message, setMessage] = useState<String>("");
  const [status, setStatus] = useState<Status>("success");
  const [visible, setVisible] = useState<boolean>(false);
  const [title, setTitle] = useState<String>("Sucesso!");


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


  const setUserData = async () =>{
    const login = localStorage.getItem("login") || "";
    const response = await apiGetByEmail(login)
    .then((response)=>{
      const data = response?.data;
      setUser(data);
    });
  }

  useEffect(()=>{
    setUserData();
    getAlert(setMessage, setStatus, setVisible, setTitle);
    setRole(localStorage.getItem("role") || "participant");
  }, [])
  
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
