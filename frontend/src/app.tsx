import { Router, useLocation, useNavigate } from "@solidjs/router";
import { FileRoutes } from "@solidjs/start/router";
import { createEffect, Suspense } from "solid-js";
import "./app.css";
import { AuthProvider, useAuth } from "./auth-provider";
import Nav from "./components/nav";

function AuthGuard(props: any) {
	const auth = useAuth();
	const navigate = useNavigate();
	const location = useLocation();

	createEffect(() => {
		const pathname = location.pathname;
		if (
			!auth.isAuthenticated() &&
			pathname !== "/login" &&
			pathname !== "/register"
		) {
			navigate("/login", { replace: true });
		}
	});

	return <>{props.children}</>;
}

export default function App() {
	return (
		<Router
			root={(props) => (
				<AuthProvider>
					<AuthGuard>
						<Nav />
						<Suspense>{props.children}</Suspense>
					</AuthGuard>
				</AuthProvider>
			)}
		>
			<FileRoutes />
		</Router>
	);
}
