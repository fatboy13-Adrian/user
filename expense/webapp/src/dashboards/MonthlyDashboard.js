import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import MonthlyTable from "../components/tables/MonthlyTable";
import "../styles/dashboards/MonthlyDashboard.css";
import SelectMonth from '../components/selections/SelectMonth';

export default function MonthlyDashboard () {
    const [expenses, setMonthly] = useState([]);
    const [month, setMonth] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        setError("");
        axios.get("http://localhost:8080/monthly")
        .then((res) => {
            setMonthly(res.data);
        }).catch((error) => {
            setError(error.response?.data?.message || 
            "Failed to retrieve budget records.");
        }).finally(() => {
            setLoading(false);
        });
    }, []);

    const filteredExpenses = expenses
    .filter((expense) => expense.month)
    .filter((expense) => {
        const expenseMonth = 
        new Date(expense.month);
        const matchedMonths = month === "" || 
        expenseMonth.toISOString()
        .slice(0,7) === month;
        return matchedMonths
    });

    const downloadExcel = async () => {
        const year = new Date()
        .getFullYear();
        const response = 
        await fetch(`http://localhost:8080/budgets/export/${year}`, {
            method: "GET"
        });

        const blob = 
        await response
        .blob();
        const url = 
        window.URL
        .createObjectURL(blob);
        const link = 
        document.createElement("a");
        link.href = url;
        link.download = 
        `Budget Records For Year ${year}.xlsx`;
        document.body.appendChild(link);
        link.click();
        link.remove();
        window.URL.revokeObjectURL(url);
    };

    return (
        <div className = "monthlyDashboard-center">
        <h1>View Monthly Records</h1>
        <div className = "filter-section">
            <SelectMonth 
            name = "month" 
            value = {month} 
            onChange = {(e) => setMonth(e.target.value)}
        />

        {loading ? (
                <p>Loading...</p>
            ) : error ? (
                <div className = "error-message" role = "alert">
                    {error}
                </div>
            ) : (
                <>
                <div className = "month-table-wrapper">
                    <MonthlyTable monthlyExpenses = 
                    {filteredExpenses}/>
                </div>

                <div className = "buttons-center">
                <button className = "custom-btn" 
                onClick = {() => setMonth("")}>
                    Clear
                </button>
                <button className = "custom-btn" 
                onClick = {downloadExcel}>
                    Download
                </button>
                <button className = "custom-btn" 
                onClick = {() => navigate("/yearly/retrieve")}>
                    View Yearly
                </button>
                <button className = "custom-btn" 
                onClick = {() => navigate("/home")}>
                    Home
                </button>
                </div>
                </>
            )}
        </div>
        </div>
    );
}