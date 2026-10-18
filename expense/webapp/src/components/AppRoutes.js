import { Routes, Route, Navigate } from "react-router-dom";
import ExpenseForm from "../ExpenseForm";
import ExpenseDashboard from "../dashboards/ExpenseDashboard";

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/expenses/save" element={<ExpenseForm />} />
            <Route path="/expenses/dates" element={<ExpenseDashboard />} />
            <Route path="/" element={<Navigate to="/expenses/dates" replace />} />
            <Route path="*" element={<Navigate to="/expenses/dates" replace />} />
        </Routes>
    );
}