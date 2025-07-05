import { z } from 'zod';

const emailSchema = z
  .string({ required_error: 'E-mail é obrigatório' })
  .email('E-mail inválido');

const passwordSchema = z
  .string({ required_error: 'Senha é obrigatória' })
  .min(6, 'Senha precisa ter pelo menos 6 dígitos')
  .max(40, 'Também não exagera né');

export const LoginSchema = z.object({
  email: emailSchema,
  password: passwordSchema,
});

export const EmailSchema = z.object({
  email: emailSchema,
});

export const PasswordSchema = z
  .object({
    password: passwordSchema,
    confirmPassword: z
      .string()
      .min(6, 'Senha precisa ter pelo menos 6 dígitos')
      .max(40, 'Senha muito longa'),
  })
  .refine(data => data.password === data.confirmPassword, {
    message: 'As senhas não coincidem',
    path: ['confirmPassword'],
  });

export type LoginType = z.infer<typeof LoginSchema>;
export type EmailType = z.infer<typeof EmailSchema>;
export type PasswordType = z.infer<typeof PasswordSchema>;
