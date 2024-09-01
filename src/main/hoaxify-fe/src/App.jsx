import { Outlet } from 'react-router-dom'
import TopBar from './shared/components/TopBar'
import Navbar from './shared/components/NavBar/NavBar'
import { LanguageSelector } from "./shared/components/LanguageSelector";
function App() {

  return (
    <>
    <Navbar/>
    <div className='container'>
     <Outlet/>
    </div>
    </>
  )
}

export default App
