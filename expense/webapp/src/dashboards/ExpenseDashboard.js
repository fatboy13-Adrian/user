import React, {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import ExpenseTable from "../components/tables/ExpenseTable";
import "../styles/dashboards/ExpenseDashboard.css";

export default function ExpenseDashboard() {
    const [expenses, setExpenses] = useState([]);
    const [selectedMonth, setSelectedMonth] = 
    useState(() => {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1)
        .padStart(2, '0');
        return `${year}-${month}`;
   });
    
    //Pagination State variables
    const [currentPage, setCurrentPage] = 
    useState(1);
    const [totalPages, setTotalPages] = 
    useState(1);
    const [pageSize] = 
    useState(10); 

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    //Re-fetch from DB whenever page selection changes
    useEffect(() => {
        setLoading(true);
        setError("");
        axios.get("http://localhost:8080/expenses/dates", {
            params: {
                page: currentPage, 
                size: pageSize 
            }
       }).then((res) => {
                const contentArray = 
                res.data && 
                res.data.content ? 
                res.data.content : [];
                setExpenses(contentArray);
                setTotalPages(res.data.totalPages || 1);
           }).catch((error) => {
                setError(error.response?.data?.message || 
                "Failed to retrieve expense list");
           }).finally(() => {
                setLoading(false);
           });
   }, [currentPage, pageSize]); 

    const filteredExpenses = expenses
    .filter((expense) => expense && expense.date)
    .filter((expense) => {
        if (selectedMonth === "") return true;
        return expense.date.substring(0, 7) === selectedMonth;
    }).sort((a, b) => 
    new Date(b.date) - new Date(a.date));

    const handleResetToCurrentMonth = () => {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1)
        .padStart(2, '0');
        setSelectedMonth(`${year}-${month}`);
        setCurrentPage(1); 
   };

    return ( 
        <div className = "expenseDashboard-center">
            <h1>View Daily Expense Records</h1>
            <div className = "filter-section">
                <input 
                    type = "month" 
                    value = {selectedMonth} 
                    onChange = {(e) => {
                        setSelectedMonth(e.target.value);
                        setCurrentPage(1); 
                   }} 
                    className = "input" 
                    max = {new Date()
                    .toISOString()
                    .substring(0, 7)}
                />
            </div>

            {loading ? (
                <p>Loading...</p>
            ) : error ? (
                <div className = "error-message" role="alert">
                    {error}
                </div>
            ) : (
                <>
                    <div className = "expense-table-wrapper">
                        <ExpenseTable expenses = {filteredExpenses}/>
                    </div>

                    {/* Pagination Container Wrapper with vertical spacing layout */}
                    <div className = "pagination-container">
                        <div className = "pagination-controls">
                            <button 
                                disabled={currentPage === 1} 
                                onClick={() => 
                                setCurrentPage((prev) => prev - 1)}
                                className = "page-btn"
                            >
                                Previous
                            </button>
                            <span className = "page-info">
                                Page <strong>{currentPage}
                                </strong> of {totalPages}
                            </span>
                            <button 
                                disabled={currentPage === totalPages} 
                                onClick={() => 
                                setCurrentPage((prev) => prev + 1)}
                                className = "page-btn"
                            >
                                Next
                            </button>
                        </div>
                    </div>

                    <div className = "buttons-center">
                        <button className ="custom-btn" onClick={() => 
                        navigate("/expenses/save")}>
                            New
                        </button>
                        <button className = "custom-btn" onClick={() => 
                        {setSelectedMonth(""); setCurrentPage(1);}}>
                            Show All
                        </button>
                        <button className = "custom-btn" onClick = 
                        {handleResetToCurrentMonth}>
                            Current Month
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}