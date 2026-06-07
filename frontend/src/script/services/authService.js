import { API_BASE_URL, getErrorMessage, parseResponse } from "./api.js";

export async function login(email, password) {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({
            email: email.trim(),
            password
        })
    });

    const payload = await parseResponse(response);

    if (!response.ok) {
        throw new Error(getErrorMessage(payload, "Invalid email or password"));
    }

    return payload;
}

export async function logout() {
    const response = await fetch(`${API_BASE_URL}/auth/logout`, {
        method: "POST",
        credentials: "include"
    });

    if (!response.ok) {
        const payload = await parseResponse(response);
        throw new Error(getErrorMessage(payload, "Unable to logout"));
    }

    window.location.href = "/frontend/src/pages/login.html";
}
