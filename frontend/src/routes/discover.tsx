import { useNavigate } from "@solidjs/router";
import { createSignal, For, onMount } from "solid-js";
import { getContainers } from "~/lib/api";
import type { Container } from "~/lib/types";

export default function Discover() {
	const navigate = useNavigate();
	const [searchQuery, setSearchQuery] = createSignal("");
	const [containers, setContainers] = createSignal<Container[]>([]);
	const [loading, setLoading] = createSignal(true);
	const [error, setError] = createSignal<string | null>(null);

	onMount(async () => {
		try {
			const data = await getContainers();
			setContainers(data);
		} catch (err) {
			setError(
				err instanceof Error ? err.message : "Failed to load containers",
			);
		} finally {
			setLoading(false);
		}
	});

	const toggleLike = async (id: string) => {
		try {
			setContainers((prev) =>
				prev.map((container) =>
					container.id === id
						? { ...container, liked: !container.liked }
						: container,
				),
			);
		} catch (err) {
			console.error("Failed to toggle like:", err);
		}
	};

	return (
		<div class="min-h-screen bg-[oklch(0.877_0.1077_329.54/24%)] pt-8 pb-16">
			{/* Search Bar */}
			<div class="max-w-4xl mx-auto px-8 mb-12">
				<div class="relative">
					<input
						type="text"
						placeholder="suchbegriff"
						value={searchQuery()}
						onInput={(e) => setSearchQuery(e.currentTarget.value)}
						class="w-full px-6 py-4 text-2xl border-b-4 border-black bg-transparent focus:outline-none placeholder:text-black/70"
					/>
					<button
						type="button"
						class="absolute right-4 top-1/2 -translate-y-1/2"
					>
						<svg
							class="w-8 h-8"
							fill="none"
							stroke="currentColor"
							viewBox="0 0 24 24"
						>
							<title>Search</title>
							<path
								stroke-linecap="round"
								stroke-linejoin="round"
								stroke-width="2"
								d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
							/>
						</svg>
					</button>
				</div>
			</div>

			{/* Loading State */}
			{loading() && (
				<div class="max-w-6xl mx-auto px-8 text-center">
					<p class="text-2xl text-gray-600">Loading containers...</p>
				</div>
			)}

			{/* Error State */}
			{error() && (
				<div class="max-w-6xl mx-auto px-8 text-center">
					<p class="text-2xl text-red-600">Error: {error()}</p>
				</div>
			)}

			{/* Cards Grid */}
			{!loading() && !error() && (
				<div class="max-w-6xl mx-auto px-8 space-y-6">
					<For each={containers()}>
						{(gamble) => (
							<div
								class="bg-linear-to-r from-purple-300 to-purple-400 rounded-3xl p-8 shadow-lg hover:shadow-xl transition-shadow cursor-pointer"
								onClick={() => navigate(`/container/${gamble.id}`)}
							>
								<div class="flex gap-6">
									{/* Main Image */}
									<div class="shrink-0">
										<div class="w-72 h-48 bg-gray-300 rounded-lg overflow-hidden">
											<img
												src={`https://placehold.co/288x192?text=${encodeURIComponent(gamble.name)}`}
												alt={gamble.name}
												class="w-full h-full object-cover"
											/>
										</div>
										{/* Thumbnails */}
										<div class="flex gap-3 mt-4">
											<For each={gamble.thumbnails}>
												{() => (
													<div class="w-20 h-20 bg-gray-300 rounded overflow-hidden">
														<img
															src="https://placehold.co/80x80"
															alt="Thumbnail"
															class="w-full h-full object-cover"
														/>
													</div>
												)}
											</For>
										</div>
									</div>

									{/* Info Section */}
									<div class="grow flex flex-col justify-between py-2">
										<div class="text-white">
											<h3 class="text-3xl font-semibold mb-4">{gamble.name}</h3>
											<p class="text-xl">
												Gebot :{" "}
												{gamble.bids.length > 0
													? gamble.bids[gamble.bids.length - 1].amount
													: 0}{" "}
												CHF
											</p>
										</div>
									</div>

									{/* Like Button */}
									<div class="shrink-0 flex items-start pt-2">
										<button
											type="button"
											onClick={(e) => {
												e.stopPropagation();
												toggleLike(gamble.id);
											}}
											class="hover:scale-110 transition-transform"
										>
											<svg
												class="w-16 h-16"
												fill={gamble.liked ? "purple-950" : "white"}
												stroke="purple-950"
												stroke-width="3"
												viewBox="0 0 24 24"
											>
												<title>Like</title>
												<path
													stroke-linecap="round"
													stroke-linejoin="round"
													d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
												/>
											</svg>
										</button>
									</div>
								</div>
							</div>
						)}
					</For>
				</div>
			)}
		</div>
	);
}
