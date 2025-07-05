import type { ContractType, WorkMode } from '../types';

export const currencyFormatter = (value: number) => {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(value);
};

export const dateFormatter = (date: string | Date) =>
  new Date(date).toLocaleString();

export const partialDateFormatter = (date: string | Date) =>
  new Date(date).toLocaleDateString();

export const dateStringFormatter = (date: string | Date) => {
  const locale = navigator.language;
  return new Date(date).toLocaleDateString(locale, {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
  });
};

export const dateStringComplete = (date: string | Date) => {
  const locale = navigator.language;
  return new Date(date).toLocaleString(locale, {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  });
};

export const translateContractType = (contractType: ContractType) => {
  if (contractType === 'FULL_TIME') return 'CLT';
  if (contractType === 'INTERN') return 'Estágio';
  if (contractType === 'TRAINEE') return 'Trainee';
};

export const translateWorkMode = (workMode: WorkMode) => {
  if (workMode === 'REMOTE') return 'Remoto';
  if (workMode === 'ONSITE') return 'Presencial';
  if (workMode === 'HYBRID') return 'Híbrido';
};

export const translateGender = (gender: string) => {
  if (gender === 'MALE') return 'Masculino';
  if (gender === 'FEMALE') return 'Feminino';

  return 'Prefiro não responder';
};

export const translateStatus = (status: string) => {
  if (status === 'PENDING') return 'Em análise';
  if (status === 'INTERVIEW') return 'Fase de Entrevistas';
  if (status === 'REJECTED') return 'Não aprovado';
  if (status === 'APPROVED') return 'APROVADO';

  return 'Prefiro não responder';
};
