import { useNavigate, useParams } from "@solidjs/router";
import { ArrowLeft } from "lucide-solid";
import { createResource, createSignal, For, Show, Suspense } from "solid-js";
import { Button } from "~/components/ui/button";
import { Card, CardContent } from "~/components/ui/card";
import {
	Carousel,
	CarouselContent,
	CarouselItem,
	CarouselNext,
	CarouselPrevious,
} from "~/components/ui/carousel";
import { getContainerById } from "~/lib/api";
import type { Container } from "~/lib/types";

export default function Container() {
	const navigate = useNavigate();
	const params = useParams();

	const [name, setName] = createSignal("");
	const [bid, setBid] = createSignal("");

	const [container] = createResource(() => params.id, getContainerById);

	const images = () => ["https://placehold.co/600x400"];

	return (
		<Suspense
			fallback={
				<div class="min-h-screen bg-white flex items-center justify-center">
					<p class="text-xl text-gray-600">Loading...</p>
				</div>
			}
		>
			<Show when={container.error}>
				{(err) => (
					<div class="min-h-screen bg-white flex items-center justify-center px-8">
						<div class="text-center">
							<p class="text-xl text-red-600 mb-4">Error: {err()}</p>
							<Button onClick={() => navigate("/")}>Back to Home</Button>
						</div>
					</div>
				)}
			</Show>
			<Show when={container()}>
				{(data) => (
					<div class="min-h-screen bg-white">
						{/* Back Button */}
						<div class="p-8 pb-4">
							<button
								type="button"
								onClick={() => navigate("/discover")}
								class="flex items-center gap-2 text-purple-600 hover:text-purple-700 transition-colors text-lg pb-2 border-b-2 border-purple-600 w-fit"
							>
								<ArrowLeft class="w-5 h-5" />
								Zurück
							</button>
							<h1 class="text-2xl font-semibold text-gray-900 mt-4">
								{data().name}
							</h1>
						</div>

						<div class="grid grid-cols-1 lg:grid-cols-2 gap-8 px-8 pb-8 max-w-[1400px] mx-auto">
							{/* Left Section - Images */}
							<div class="space-y-4">
								{/* Main Image Carousel */}
								<Carousel class="w-full max-w-[600px]" opts={{ loop: true }}>
									<CarouselContent>
										<For each={images()}>
											{(image) => (
												<CarouselItem>
													<div class="relative bg-gray-200 rounded-lg overflow-hidden aspect-[4/3]">
														<img
															src={image}
															alt="Product"
															class="w-full h-full object-cover"
														/>
													</div>
												</CarouselItem>
											)}
										</For>
									</CarouselContent>
									<CarouselPrevious />
									<CarouselNext />
								</Carousel>

								{/* Description */}
								<Card class="border-gray-300">
									<CardContent class="pt-6">
										<p class="text-gray-800 leading-relaxed">
											{data().description}
										</p>
									</CardContent>
								</Card>
							</div>

							{/* Right Section - Bid Form */}
							<div class="lg:sticky lg:top-8 h-fit">
								<Card class="bg-pink-300 rounded-3xl shadow-xl border-0">
									<CardContent class="p-8">
										{/* Name Input */}
										<div class="mb-6">
											<label
												for="name-input"
												class="block text-white text-lg mb-2"
											>
												Name
											</label>
											<input
												id="name-input"
												type="text"
												value={name()}
												onInput={(e) => setName(e.currentTarget.value)}
												class="w-full bg-transparent border-b-2 border-white text-white placeholder-white/70 outline-none pb-2 text-lg"
												placeholder=""
											/>
										</div>

										{/* Bid Input */}
										<div class="mb-8">
											<label
												for="bid-input"
												class="block text-white text-lg mb-2"
											>
												Gebot
											</label>
											<input
												id="bid-input"
												type="text"
												value={bid()}
												onInput={(e) => setBid(e.currentTarget.value)}
												class="w-full bg-transparent border-b-2 border-white text-white placeholder-white/70 outline-none pb-2 text-lg"
												placeholder="--,--"
											/>
										</div>

										{/* Buttons */}
										<div class="space-y-3">
											<Button
												type="button"
												class="w-full bg-white text-purple-900 hover:bg-gray-100 h-12 text-lg font-semibold rounded-xl"
											>
												Bieten
											</Button>
											<Button
												type="button"
												variant="outline"
												class="w-full bg-purple-900/80 text-white hover:bg-purple-900 border-none h-12 text-lg rounded-xl"
											>
												zu favoriten hinzufügen
											</Button>
										</div>
									</CardContent>
								</Card>
							</div>
						</div>
					</div>
				)}
			</Show>
		</Suspense>
	);
}
