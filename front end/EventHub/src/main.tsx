import './index.css'
import React, { ReactNode } from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from './assets/routes/home/Login.tsx'
import Dashboard from './assets/routes/dashboard/Dashboard.tsx'
import Register from './assets/routes/home/Register.tsx'
import { IconDefinition } from '@fortawesome/fontawesome-svg-core'
import NextEvents from './assets/routes/dashboard/nextEvents/NextEvents.tsx'
import { faCreditCard, faList, faMapLocationDot, faTicket, faUser } from '@fortawesome/free-solid-svg-icons'
import Profile from './assets/routes/dashboard/profile/Profile.tsx'
import UpdateProfile from './assets/routes/dashboard/profile/UpdateProfile.tsx'
interface PrivateRouteProps {
  redirect: string;
  children: React.ReactNode;
}

const PrivateRoute=({redirect, children}:PrivateRouteProps)=>{
  const authenticated:boolean = localStorage.getItem("token") !== null;
  return authenticated ? children : <Navigate to={redirect} />;
}

export type Role = "participant" | "admin" | "promoter" | "attraction" | String;

export interface NavItem {
    text?:String;
    icon?:IconDefinition;
    pathRoot:String;
    path:String;
    component:ReactNode;
    permission:Array<Role>;
    navbar?:boolean
}

const routes:Array<NavItem> = [
  {text:"Dashboard", icon:faList, pathRoot:"/dashboard", path:"/dashboard", component: <NextEvents />, permission:["participant","admin","attraction"], navbar:true},
  {text:"Meus eventos", icon:faList, pathRoot:"/dashboard/myEvents", path:"/dashboard/myEvents", component: <NextEvents/>, permission:["promoter"], navbar:true},
  {text:"Perfil", icon:faUser, pathRoot:"/dashboard/profile", path:"/dashboard/profile", component: <Profile/>, permission:["participant","admin","attraction","promoter"], navbar:true},
  {pathRoot:"/dashboard/profile", path:"/dashboard/profile/updateProfile", component: <UpdateProfile/>, permission:["participant","admin","attraction","promoter"]},
  {text:"Meus ingressos", pathRoot:"/dashboard/myTickets", icon:faTicket, path:"/dashboard/myTickets", component: <NextEvents/>, permission:["participant","admin","attraction"], navbar:true},
  {text:"Cartão de crédito", pathRoot:"/dashboard/creditCard", icon:faCreditCard, path:"/dashboard/creditCard", component: <NextEvents/>, permission:["participant","admin","attraction"], navbar:true},
  {text:"Eventos participados", icon:faList, pathRoot:"/dashboard/pastEvents", path:"/dashboard/pastEvents", component: <NextEvents/>, permission:["attraction"], navbar:true},  
  {text:"Usuário", icon:faList, pathRoot:"/admin/users", path:"/admin/users", component: <NextEvents/>, permission:["admin"], navbar:true},
  {text:"Eventos", icon:faList, pathRoot:"/admin/events", path:"/admin/events", component: <NextEvents/>, permission:["admin"], navbar:true},
  // AS NOVAS ROTAS SÃO COLOCADAS AQUI DEFININDO OBJETOS DO TIPO NAVITEM
] 

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        {/* PRIVATE ROUTES */}
        {
          routes.map((route, index)=>{
            return (
              <Route
                key={`route-${index}`}
                path={route.path.toString()}
                element={<PrivateRoute redirect='/'><Dashboard routes={routes} /></PrivateRoute>}
              />
            )})
        }
        {/* PUBLIC ROUTES */}
        <Route path='/' element={<Login/>} />
        <Route path='/register/' element={<Register/>} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
)
