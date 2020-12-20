import { NgModule } from "@angular/core"
import { RouterModule, Routes } from "@angular/router"
import { DoctorComponent } from "./pages/doctor/doctor.component"
import { FacilitiesComponent } from "./pages/facilities/facilities.component"
import { FacilityComponent } from "./pages/facility/facility.component"
import { HoursComponent } from "./pages/hours/hours.component"
import { TestComponent } from "./pages/test/test.component"
import { UsersComponent } from "./pages/test/users/users.component"

const routes: Routes = [
    { path: "", redirectTo: "facilities", pathMatch: "full" },
    {
        path: "facilities",
        component: FacilitiesComponent,
    },
    {
        path: "facility/:id",
        component: FacilityComponent,
    },
    {
        path: "doctor/:id",
        component: DoctorComponent,
    },
    {
        path: "hours/:id",
        component: HoursComponent,
    },
    {
        path: "test-page",
        component: TestComponent,
    },
    {
        path: "users",
        component: UsersComponent,
    },
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
