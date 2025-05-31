package com.bebra.betting.utils.mappers;

import com.bebra.betting.models.entity.Match;
import com.bebra.betting.models.entity.MatchEvent;
import org.bebra.dto.MatchDto;
import org.bebra.dto.MatchEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author max_pri
 */
@Mapper(componentModel = "spring")
public interface MatchMapper {
    @Mapping(target = "firstPlayerName", source = "firstPlayer.name")
    @Mapping(target = "secondPlayerName", source = "secondPlayer.name")
    @Mapping(target = "leagueName", source = "league.name")
    @Mapping(target = "countryName", source = "country.name")
    @Mapping(target = "matchEvents", expression = "java(match.getMatchEvents().stream().map(matchEvent -> toMatchEventDto(match, matchEvent)).collect(java.util.stream.Collectors.toSet()))")
    MatchDto toDto(Match match);

    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "matchFirstPlayerName", source = "match.firstPlayer.name")
    @Mapping(target = "matchSecondPlayerName", source = "match.secondPlayer.name")
    @Mapping(target = "matchDate", source = "match.date")
    @Mapping(target = "id", source = "matchEvent.id")
    MatchEventDto toMatchEventDto(Match match, MatchEvent matchEvent);

    List<MatchDto> toDto(List<Match> matches);
}
