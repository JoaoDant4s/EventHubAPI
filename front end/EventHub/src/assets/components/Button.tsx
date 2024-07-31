
import { FontAwesomeIcon, FontAwesomeIconProps } from '@fortawesome/react-fontawesome'
import { faEnvelope, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { faLock } from '@fortawesome/free-solid-svg-icons';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { ComponentProps } from 'react';
import { tv, VariantProps } from 'tailwind-variants'

const button = tv({
    base: ' p-2 px-4 flex items-center justify-between text-bg-white font-bold rounded-md',
    variants: {
        color: {
            default: 'bg-gradient-to-r from-primary to-secondary shadow-sm',
            transparency: 'bg-transparency text-primary',
        },
        size: {
            full: 'w-full',
            half: 'w-1/2',
            default: 'w-60',
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

export default function Button({children, color, size, className, icon, ...props}:ButtonProps) {


  return (
    <button className={button({color, size, className})} {...props} >
        {children}
        {icon!=null
            ?<FontAwesomeIcon icon={icon} className=' text-white text-[1.4rem] ' />
            : ''
        }
    </button>
  )
}
