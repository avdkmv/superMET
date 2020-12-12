import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { TestComponent } from "./pages/test/test.component";
import { UsersComponent } from "./pages/test/users/users.component";

const routes: Routes = [
    { path: "", redirectTo: "test-page", pathMatch: "full" },
    {
        path: "test-page",
        component: TestComponent,
    },
    {
        path: "users",
        component: UsersComponent,
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
