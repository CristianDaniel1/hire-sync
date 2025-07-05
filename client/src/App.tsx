import { createBrowserRouter, RouterProvider } from 'react-router';
import Home from './pages/Home';
import RootLayout from './pages/RootLayout';
import Login from './pages/Login';
import Jobs from './pages/Jobs';
import { QueryClientProvider } from '@tanstack/react-query';
import { queryClient } from './libs/tanstackQuery';
import { Toaster } from 'sonner';
import { createAuthLoader, loggedLoader } from './utils/loaders/authLoader';
import JobDetails from './pages/JobDetails';
import CandidateProfile from './pages/CandidateProfile';
import MyCompany from './pages/MyCompany';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        index: true,
        element: <Home />,
      },
      {
        path: 'login',
        element: <Login />,
        loader: loggedLoader,
      },
      {
        path: 'vagas',
        element: <Jobs />,
      },
      {
        path: 'vagas/:jobId',
        element: <JobDetails />,
      },
      {
        path: 'candidatos/perfil',
        element: <CandidateProfile />,
        loader: createAuthLoader(['CANDIDATE']),
      },
      {
        path: 'minha-empresa/perfil',
        element: <MyCompany />,
        loader: createAuthLoader(['COMPANY']),
      },
    ],
  },
]);

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Toaster position="top-right" richColors />
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}

export default App;
