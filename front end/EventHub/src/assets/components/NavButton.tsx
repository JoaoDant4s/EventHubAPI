
import { FontAwesomeIcon, FontAwesomeIconProps } from '@fortawesome/react-fontawesome'
import { faEnvelope, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { faLock } from '@fortawesome/free-solid-svg-icons';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { ComponentProps } from 'react';
import { tv, VariantProps } from 'tailwind-variants'

const button = tv({
    base: ' p-2 px-4 flex items-center text-bg-white font-bold rounded-md',
    variants: {
        color: {
            default: 'bg-gradient-to-r from-primary to-secondary',
            transparency: 'bg-transparency text-font-navItem',
        },
        size: {
            full: 'w-full',
            half: 'w-1/2',
        }
    },
    defaultVariants: {
        color: 'default',
        size: 'full',
    }
})

export type ButtonProps = ComponentProps<'button'> & VariantProps<typeof button> & {
    icon?:IconDefinition
} 

export default function NavButton({children, color, size, className, icon, ...props}:ButtonProps) {


  return (
    <button className={button({color, size, className})} {...props} >
        <div className=' w-8 h-full flex justify-center items-center '>
            {icon!=null
            ? color == 'default'
                ? <FontAwesomeIcon icon={icon} className=' text-white text-[1.4rem] text-lg' />
                : <FontAwesomeIcon icon={icon} className=' text-font-nav text-[1.4rem] text-lg' />
            : ''
            }
        </div>
        <div className='ml-4'>
        {children}
        </div>
    </button>
  )
}
