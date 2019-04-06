package pl.prodzajto.estolowkabackend.order;

import java.time.LocalDate;

interface DuplicatedDayProjection
{
    LocalDate getDuplication();
}
