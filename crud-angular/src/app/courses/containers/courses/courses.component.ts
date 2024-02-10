import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { CoursesService } from '../../services/courses.service';
import { Course } from '../../model/course';
import { Component, OnInit } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit{

  courses$: Observable<Course[]> | null = null;
  displayedColumns = ['name', 'category', 'actions']

  constructor(
    public dialog: MatDialog,
    private coursesService : CoursesService,
    private router: Router,
    private route: ActivatedRoute
  ){
    this.refresh();
  }

  onError(error: string){

    this.dialog.open(ErrorDialogComponent, {
      data: error
    });

  }

  ngOnInit(): void {}

  onAdd(){
    this.router.navigate(['new'], {relativeTo: this.route});
    this.refresh();
  }

  onEdit(course: Course) {
    this.router.navigate(['edit', course._id], {relativeTo: this.route});
    this.refresh();
  }

  onDelete(course: Course) {
    let dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: 'Deseja excluir este curso?',
    });

    dialogRef.afterClosed().subscribe((result : boolean) => {
      if(result){
        this.coursesService.remove(course._id);
        this.refresh();
      }
    });

  }

  refresh(){
    this.courses$ = this.coursesService.list()
    .pipe(
      catchError(error => {
        this.onError('Erro ao carrgar cursos.')
        return of([])
      })
    );
  }
}
