import { type ComponentPropsWithoutRef } from 'react';

type InputProps = {
  label: string;
  id: string;
  className?: string;
} & ComponentPropsWithoutRef<'input'>;

export const Input = ({ label, id, className = '', ...props }: InputProps) => {
  return (
    <>
      <label htmlFor={id} className="block font-medium pb-2">
        {label}
      </label>
      <input
        id={id}
        name={id}
        {...props}
        className={`w-full py-3 px-4 text-primary-dark border border-primary-dark/50 rounded-md ${className}`}
      />
    </>
  );
};
