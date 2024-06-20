package org.beatengine.onlineshop.entity;

import java.lang.reflect.Field;

public class SuperEntity {

    /**
     * @return A JSON String that contains the fields with values for the Entity, except the relation fields.
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder("{\"class\":");
        builder.append("\"").append(this.getClass().getSimpleName()).append("\"");
        builder.append(", \"object\": { ");
        boolean first = true;
        for (final Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                final Object value = field.get(this);
                if(value instanceof Iterable || value instanceof SuperEntity)
                {
                    // It's a list of related objects, we don't print this field
                    // Or it's a related Object
                }
                else {
                    if (!first) {
                        builder.append(", ");
                    }
                    builder.append("\"").append(field.getName()).append("\": ");

                    if (value == null) {
                        builder.append("null");
                    } else {
                        builder.append("\"").append(value.toString()).append("\" ");
                    }
                }

            } catch (IllegalAccessException accessException)
            {
                builder.append("{\"error\":\"NoAccess\"}");
            }
            first = false;
        }

        return builder.append("}}").toString();
    }

}
