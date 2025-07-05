export type WorkMode = 'REMOTE' | 'ONSITE' | 'HYBRID';

export type ContractType = 'INTERN' | 'TRAINEE' | 'FULL_TIME';

export interface Job {
  id: string;
  title: string;
  description: string;
  location: string;
  salary: number;
  workMode: WorkMode;
  contractType: ContractType;
  requirements: string;
  responsibilities: string;
  courseRequired: string;
  graduationYearRequired: string;
  minSemester: number;
  expiresAt: string;
  createdAt: string;
  modifiedAt: string;
  companyId: string;
  companyName: string;
  companyLogo: string;
  website: string;
  address: string;
}

export interface PaginatedResponse<T> {
  page: number;
  limit: number;
  totalElements: number;
  totalPages: number;
  isLast: boolean;
  content: T[];
}

export type PaginatedJobsResponse = PaginatedResponse<Job>;

export type Role = 'COMPANY' | 'CANDIDATE' | 'ADMIN';

export interface CandidateUser {
  type?: 'CANDIDATE';
  id: string;
  name: string;
  email: string;
  course: string;
  address: string;
  cpf: string;
  birthDate: string;
  graduationYear: string;
  currentlyStudying: boolean;
  currentSemester: number;
  gender: string;
  phone: string;
  roles: Role[];
}

export interface CompanyUser {
  type?: 'COMPANY';
  id: string;
  name: string;
  email: string;
  cnpj: string;
  logoImage: string;
  phone: string;
  website: string;
  address: string;
  isHeadOffice: boolean;
  groupName: string;
  roles: Role[];
}

interface ResumeResponse {
  id: string;
  summary: string;
  skills: string;
  languages: string;
  certifications: string;
  githubUrl: string;
  portfolioUrl: string;
  linkedinUrl: string;
  repositoryUrl: string;
  fileUrl: string;
  createdAt: string;
  modifiedAt: string;
}

interface JobApplicationResponse {
  id: string;
  status: string;
  createdAt: string;
  modifiedAt: string;
  candidateId: string;
  candidateName: string;
  candidateEmail: string;
  jobId: string;
  jobTitle: string;
  companyName: string;
}

export type UserAuth = CandidateUser | CompanyUser;

export interface AuthResponse {
  user: UserAuth;
  token: string;
}

export interface BodyLogin {
  email: string;
  password: string;
}

export interface JobReport {
  id: string;
  title: string;
  location: string;
  expiresAt: string;
  totalApplications: number;
}
