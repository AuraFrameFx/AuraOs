/**
 * Precompiled [room-database-convention.gradle.kts][Room_database_convention_gradle] script plugin.
 *
 * @see Room_database_convention_gradle
 */
public
class RoomDatabaseConventionPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Room_database_convention_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
