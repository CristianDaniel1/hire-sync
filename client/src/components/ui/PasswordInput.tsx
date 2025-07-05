import { useState, type ComponentPropsWithoutRef } from 'react';
import { EyeSlashIcon } from '../icons/EyeSlashIcon';
import { EyeIcon } from '../icons/EyeIcon';

type InputProps = {
  id: string;
  label: string;
  className?: string;
} & ComponentPropsWithoutRef<'input'>;

export const PasswordInput = ({
  id,
  label,
  className = '',
  ...props
}: InputProps) => {
  const [passwordIsVisible, setPasswordIsVisible] = useState(false);

  function handleTogglePassword() {
    setPasswordIsVisible(prevState => !prevState);
  }

  return (
    <div className="relative">
      <label htmlFor={id} className="block font-medium pb-2">
        {label}
      </label>
      <input
        id={id}
        name={id}
        {...props}
        type={`${passwordIsVisible ? 'text' : 'password'}`}
        className={`w-full py-3 px-4 text-primary-dark border border-primary-dark/50 rounded-md ${className}`}
      />
      <button
        type="button"
        className="absolute block border-none right-4 translate-y-1 top-1/2 duration-200"
        onClick={handleTogglePassword}
      >
        {passwordIsVisible ? <EyeSlashIcon /> : <EyeIcon />}
      </button>
    </div>
  );
};
