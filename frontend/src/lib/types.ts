export interface Container {
	id: string;
	name: string;
	description: string;
	mainImage: string;
	thumbnails: string[];
	liked: boolean;
	createdAt: string;
	updatedAt: string;
	bids: Array<{
		id: string;
		name: string;
		amount: number;
		timestamp: string;
	}>;
}

export interface CreateContainerRequest {
	name: string;
	description: string;
	images: File[];
}

export interface ToggleLikeResponse {
	liked: boolean;
}

export interface ContainerListParams {
	search?: string;
	page?: number;
	perPage?: number;
}

export interface PaginatedContainers {
	data: Container[];
	total: number;
	page: number;
	perPage: number;
	totalPages: number;
}
