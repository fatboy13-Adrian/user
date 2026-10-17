import {BrowserRouter as Router} from "react-router-dom";
import AppRoutes from "./components/AppRoutes"; 
 
function App() {
  return (
    <Router future = {{v7_startTransition: true, 
    v7_relativeSplatPath: true}}>
      <AppRoutes/>
    </Router>
  );
}

export default App;