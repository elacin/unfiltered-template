package template

import scala.collection.mutable

trait PetStoreComponent {
  object PetStore {
    private val pets = mutable.HashMap[PetId, Pet]()

    def create(p: Pet) =
      pets += p.id → p

    def read(pid: PetId): Option[Pet] =
      pets.get(pid)

    def update(pid: PetId, p: Pet) =
      pets(pid) = p

    def delete(pid: PetId) =
      pets -= pid

    def filtered(pred: Pet ⇒ Boolean): List[Pet] =
      pets.values.filter(pred).toList
  }
}