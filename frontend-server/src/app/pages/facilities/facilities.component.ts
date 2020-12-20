import { Component, OnInit } from "@angular/core"
import { ToastrService } from "ngx-toastr"
import { Facility } from "src/app/models/facility"
import { FacilityService } from "src/app/services/facility.service"

@Component({
    selector: "app-facilities",
    templateUrl: "./facilities.component.html",
    styleUrls: ["./facilities.component.sass"],
})
export class FacilitiesComponent implements OnInit {
    facilities: Array<Facility>

    constructor(private service: FacilityService, private toastr: ToastrService) {}

    ngOnInit(): void {
        this.service.getAllFacilities().subscribe(
            (res) => (this.facilities = <Array<Facility>>res),
            (err) => this.toastr.error("Failed to load facilities"),
        )
    }
}
