import type { ReactNode, ComponentPropsWithoutRef } from 'react';

type ButtonProps = {
  children: ReactNode;
  bgColor?: boolean;
  className?: string;
} & ComponentPropsWithoutRef<'button'>;

export const Button = ({
  children,
  bgColor,
  className = '',
  ...props
}: ButtonProps) => {
  return (
    <button
      className={`text-center border-2 ${
        bgColor
          ? 'bg-primary-dark-2 border-transparent text-white'
          : 'bg-transparent border-primary-dark-2 text-primary-dark-2'
      } duration-200 z-[1] ${className}`}
      {...props}
    >
      {children}
    </button>
  );
};
