import {Routes, Route, Navigate} from "react-router-dom";
import ExpenseForm from "../ExpenseForm";
import ExpenseDashboard from "../dashboards/ExpenseDashboard";
import MonthlyDashboard from "../dashboards/MonthlyDashboard";
import YearlyDashboard from "../dashboards/YearlyDashboard";
export default function AppRoutes() {
    return (
        <Routes>
            {/*
            Home & Fallback Routes: 
            Defines main home path & handles 
            redirects for root & broken links
            */}
            <Route path = "/home" element = 
            {<ExpenseDashboard/>}/>
            <Route path =  "/" element = 
            {<Navigate to = "/home" replace/>}/>
            <Route path = "*" element = 
            {<Navigate to = "/home" replace/>}/>

            {/*
            Form & Dashboard Routes:
            Defines other paths & handles
            redirects for other links
            */}
            <Route path = "/expenses/save" 
            element = {<ExpenseForm/>}/>
            <Route path = "/expenses/retrieve" 
            element = {<ExpenseDashboard/>}/>
            <Route path = "/monthly/retrieve" 
            element = {<MonthlyDashboard/>}/>
            <Route path = "/yearly/retrieve" 
            element = {<YearlyDashboard/>}/>
        </Routes>
    );
}