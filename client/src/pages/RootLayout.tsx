import { Outlet } from 'react-router';
import { ScrollToTop } from '../components/ScrollToTop.tsx';
import { Header } from '../components/header/Header.tsx';
import { Footer } from '../components/Footer.tsx';

const RootLayout = () => {
  return (
    <>
      <Header />
      <ScrollToTop />
      <main className="min-h-screen">
        <Outlet />
      </main>
      <Footer />
    </>
  );
};

export default RootLayout;
