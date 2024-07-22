import { ComponentProps } from 'react';
import { twMerge } from 'tailwind-merge';
import { tv, VariantProps } from 'tailwind-variants'
import { faArrowRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import NavButton from './NavButton';
import { useNavigate } from 'react-router-dom';

export type NavbarProps = ComponentProps<'nav'>

export default function NavBar({children, className, ...props}:NavbarProps) {

    const navigate = useNavigate();

    const logout=()=>{
        localStorage.removeItem("token");
        navigate("/");
      }

  return (
    <nav className=' bg-bg-white w-[360px] h-[100vh] shadow-xl absolute left-0 p-8 flex flex-col' {...props} >
        <div className='flex justify-center'>
            <h1 className=' text-font-title font-extrabold text-[2rem] my-10 '>EVENTHUB</h1>
        </div>
        <div className=' flex flex-col flex-grow gap-4 overflow-auto'>
            {children}
        </div>
        <div>
            <NavButton onClick={()=>logout()} icon={faArrowRightFromBracket} color='transparency'>Sair</NavButton>
        </div>
    </nav>
  )
}
