import {BehaviorSubject, Observable, Subject, take} from "rxjs";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {ListService} from "./list.service";

export class ListDataSource<S extends ListService<R, E>, R, E> extends DataSource<E> {

  private elementsSubject: Subject<E[]> = new BehaviorSubject<E[]>([]);

  public constructor(private readonly listService: S) {
    super();
  }

  public override connect(collectionViewer: CollectionViewer): Observable<E[]> {
    return this.elementsSubject.asObservable();
  }

  public override disconnect(collectionViewer: CollectionViewer): void {
    this.elementsSubject.complete();
  }

  public load(request: R): void {
    let observable: Observable<E[]> = this.listService.list(request);
    observable.pipe(take(1)).subscribe(
      (elements: E[]): void => {
        this.elementsSubject.next(elements);
      }
    );
  }

}
