import { ComponentProps } from 'react';
import { twMerge } from 'tailwind-merge';
import { tv, VariantProps } from 'tailwind-variants'
import { faArrowRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import NavButton from './NavButton';

export type ContainerProps = ComponentProps<'section'>

export default function Container({children, className, ...props}:ContainerProps) {


  return (
    <section className=' bg-bg-dashboard w-full min-h-[100vh] absolute top-0' {...props} >
        <div className='w-[calc(100% - 360px)] ml-[360px] '>
          <div className=' w-3/4 m-auto'>
            {children}
          </div>
        </div>
    </section>
  )
}
