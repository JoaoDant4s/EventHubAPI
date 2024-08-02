import { ComponentProps, ReactNode, useEffect, useState } from 'react';
import { faArrowRightFromBracket, faUser, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import NavButton from './NavButton';
import { useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavItem } from '../../main';
import { unsetStates } from '../api/services/utils';

export type NavbarProps = ComponentProps<'nav'> & {
    name?:String
    role?:String
    navItems:Array<NavItem>
    routes:Array<NavItem>
}

export default function NavBar({ name = "Username", role = "Participant", navItems, routes, className, ...props}:NavbarProps) {
    const location = useLocation();
    const navigate = useNavigate();
    

    const logout=()=>{
        unsetStates();
        navigate("/");
    }

    const setColor=(item:NavItem)=>{
        if(item.path === location.pathname || item.path === routes?.find((i)=>i.path === location.pathname)?.pathRoot){
            return "default"
        } else {
            return "transparency"
        }
    }

  return (
    <nav className=' bg-bg-white w-[360px] h-[100vh] shadow-xl fixed left-0 p-8 flex flex-col' {...props} >
        <div className='flex justify-center'>
            <h1 className=' text-font-title font-extrabold text-[2rem] my-10 '>EVENTHUB</h1>
        </div>
        <div className=' flex flex-col flex-grow gap-4 overflow-auto'>
            <div className={' pl-3 flex items-center text-bg-white font-bold rounded-md text-font-navItem'}>
                <div className=' w-10 h-10 flex justify-center items-center bg-font-navItem rounded-[50%] p-2'>
                    <FontAwesomeIcon icon={faUser} className=' text-font-placeholder ' />
                </div>
                <div className=' ml-3 flex-col justify-between '>
                    <h3>{name}</h3>
                    <p className=' text-font-text font-normal text-[0.8rem] ' >{role}</p>
                </div>
            </div>
            {navItems && navItems.map((item, index)=>{
                return (<NavButton key={`item-${index}`} icon={item.icon} color={setColor(item)} onClick={()=>navigate(item.path.toString())}>{item.text}</NavButton>)
            })}
        </div>
        <div>
            <NavButton onClick={()=>logout()} icon={faArrowRightFromBracket} color='transparency'>Sair</NavButton>
        </div>
    </nav>
  )
}
