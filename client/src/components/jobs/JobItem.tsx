import { Link } from 'react-router';
import type { ContractType, WorkMode } from '../../types';
import {
  partialDateFormatter,
  translateContractType,
  translateWorkMode,
} from '../../utils/formatting';
import { BagIcon } from '../icons/BagIcon';
import { BuildingIcon } from '../icons/BuildingIcon';
import { MonitorIcon } from '../icons/Monitor';

interface JobItemProps {
  id: string;
  companyLogo: string;
  companyName: string;
  title: string;
  workMode: WorkMode;
  location: string;
  contractType: ContractType;
  modifiedAt: string | Date;
}

export const JobItem = ({
  id,
  companyLogo,
  companyName,
  title,
  workMode,
  location,
  contractType,
  modifiedAt,
}: JobItemProps) => {
  return (
    <li className="custom-shadow-2 rounded-lg bg-white">
      <Link to={`/vagas/${id}`}>
        <div className="pb-3 p-4">
          <div className="flex gap-2 items-center">
            {companyLogo && (
              <div className="size-12">
                <img
                  src={companyLogo}
                  alt="vaga"
                  className="object-contain h-full w-full"
                />
              </div>
            )}
            <h3 className="font-medium text-primary-dark/60 truncate">
              {companyName}
            </h3>
          </div>
          <h4 className="text-lg font-medium truncate">{title}</h4>
        </div>
        <div className="px-4 flex items-center text-sm gap-1 font-medium text-primary-dark/80">
          <BagIcon className="size-4" />
          Contrato - {translateContractType(contractType)}
        </div>
        <div className="flex gap-4 items-center pb-4 p-4 border-b border-b-primary-dark/10">
          <div className="max-w-[50%] truncate">{location}</div>
          <div className="flex items-center gap-1">
            {translateWorkMode(workMode) === 'Presencial' ? (
              <BuildingIcon />
            ) : (
              <MonitorIcon />
            )}{' '}
            {translateWorkMode(workMode)}
          </div>
        </div>
        <div className="p-4 text-sm text-primary-dark/80">
          Atualizado em {partialDateFormatter(modifiedAt)}
        </div>
      </Link>
    </li>
  );
};
