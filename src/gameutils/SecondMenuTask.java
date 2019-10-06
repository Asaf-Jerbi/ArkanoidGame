package gameutils;

import game.animations.AnimationRunner;
import interfaces.Menu;
import interfaces.Task;

/**
 * The type Second menu task.
 *
 * @param <T> the type parameter
 */
public class SecondMenuTask<T> implements Task<Void> {

    private AnimationRunner ar;
    private MenuAnimation<Task<Void>> subMenuMember;


    /**
     * Instantiates a new Second menu task.
     *
     * @param ar      the ar
     * @param subMenu the sub menu
     */
    public SecondMenuTask(AnimationRunner ar, Menu<T> subMenu) {
        this.ar = ar;
        this.subMenuMember = (MenuAnimation<Task<Void>>) subMenu;
    }


    @Override
    public Void run() {
        this.subMenuMember.reset();
        ar.run(this.subMenuMember);
        // wait for user selection
        Task<Void> task = this.subMenuMember.getStatus();
        task.run();
        return null;
    }
}
