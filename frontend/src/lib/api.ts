const API_BASE_URL = "http://localhost:8080";

export async function getContainers() {
	const response = await fetch(`${API_BASE_URL}/api/containers`);
	if (!response.ok) {
		throw new Error(`Failed to fetch containers: ${response.statusText}`);
	}
	return response.json();
}

export async function getContainerById(id: string) {
	const response = await fetch(`${API_BASE_URL}/api/containers/${id}`);
	if (!response.ok) {
		throw new Error(`Failed to fetch container: ${response.statusText}`);
	}
	return response.json();
}

export async function createContainer(data: {
	name: string;
	description: string;
}) {
	const response = await fetch(`${API_BASE_URL}/api/containers`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(data),
	});
	if (!response.ok) {
		throw new Error(`Failed to create container: ${response.statusText}`);
	}
	return response.json();
}

export async function toggleLike(id: string) {
	const response = await fetch(`${API_BASE_URL}/api/containers/${id}/like`, {
		method: "PATCH",
	});
	if (!response.ok) {
		throw new Error(`Failed to toggle like: ${response.statusText}`);
	}
	return response.json();
}

export async function addBid(id: string, amount: number) {
	const response = await fetch(`${API_BASE_URL}/api/containers/${id}/bid`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({ amount }),
	});
	if (!response.ok) {
		throw new Error(`Failed to add bid: ${response.statusText}`);
	}
	return response.json();
}
