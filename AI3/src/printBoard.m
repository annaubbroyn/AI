folder = 'C:\Users\Anna\Documents\GitHub\AI\AI3\solutions\';
name = 'solution-test-2.txt';
fullname = [folder name];
data = importdata(fullname);
[rows,m] = size(data);
path_x = strsplit(data{1,1},{', ','[',']'});
path_y = strsplit(data{2,1},{', ','[',']'});
open_x = strsplit(data{3,1},{', ','[',']'});
open_y = strsplit(data{4,1},{', ','[',']'});
closed_x = strsplit(data{5,1},{', ','[',']'});
closed_y = strsplit(data{6,1},{', ','[',']'});
line = strsplit(data{7,1},{', ','[',']'});
cols = length(line);
water = zeros(rows-6,cols);
mountain = zeros(rows-6,cols);
forest = zeros(rows-6,cols);
grassland = zeros(rows-6,cols);
road = zeros(rows-6,cols);
obstical = zeros(rows-6,cols);
trace = zeros(rows-6,cols);
open = zeros(rows-6,cols);
closed = zeros(rows-6,cols);
start = zeros(rows-6,cols);
final = zeros(rows-6,cols);
for i = 1:length(path_x)
    trace(str2num(path_y{i})+1,str2num(path_x{i})+1) = 1;
end
for i = 1:length(open_x)
    if(trace(str2num(open_y{i})+1,str2num(open_x{i})+1) == 0)
        open(str2num(open_y{i})+1,str2num(open_x{i})+1) = 1;
    end
end
for i = 1:length(closed_x)
    if(trace(str2num(closed_y{i})+1,str2num(closed_x{i})+1) == 0)
        closed(str2num(closed_y{i})+1,str2num(closed_x{i})+1) = 1;
    end
end
for i = 7:rows
    line = strsplit(data{i,1},{', ','[',']'});
    for j = 2:length(line)-1
        if(str2double(line{j}) == -1)
            obstical(i-6,j-1) = 1;
        elseif(str2double(line{j}) == -2)
            start(i-6,j-1) = 1;
        elseif(str2double(line{j}) == -3)
            final(i-6,j-1) = 1;
        elseif(str2double(line{j}) == 1)
            road(i-6,j-1) = 1;
        elseif(str2double(line{j}) == 5)
            grassland(i-6,j-1) = 1;
        elseif(str2double(line{j}) == 10)
            forest(i-6,j-1) = 1;
        elseif(str2double(line{j}) == 50)
            mountain(i-6,j-1) = 1;
        elseif(str2double(line{j}) == 100)
            water(i-6,j-1) = 1;
        end
    end
end

roadColor = [0.9,0.85,0.8];
forestColor = [0,0.4,0];
grasslandColor = [0,0.7,0];
waterColor = [0,0,1];
mountainColor = [0.5,0.5,0.5];
startColor = [1,0,0];
finalColor = [0,1,0];

figure(1)
hold on
%spy(road,'sy',12)
spy(grassland,'sg',12)
spy(forest,'sy',12)
spy(mountain,'sm',12)
spy(water,'sb',12)
spy(obstical,'sk',23)
spy(trace,'.k')
spy(open,'*k')
spy(closed,'xk')
spy(start,'or',12)
spy(final,'oc',12)
markerSt = findall(gca,'color','r');
markerFi = findall(gca,'color','c');
markerOb = findall(gca,'color','k');
markerRo = findall(gca,'color','y');
markerGr = findall(gca,'color','g');
markerFo = findall(gca,'color','y');
markerMo = findall(gca,'color','m');
markerWa = findall(gca,'color','b');
set(markerRo,'MarkerFaceColor',roadColor,'MarkerEdgeColor',roadColor);
set(markerSt,'MarkerFaceColor',startColor,'MarkerEdgeColor',startColor);
set(markerFi,'MarkerFaceColor',finalColor,'MarkerEdgeColor',finalColor);
set(markerOb,'MarkerFaceColor','k','MarkerEdgeColor','k');
set(markerGr,'MarkerFaceColor',grasslandColor, 'MarkerEdgeColor',grasslandColor);
set(markerFo,'MarkerFaceColor',forestColor, 'MarkerEdgeColor',forestColor);
set(markerMo,'MarkerFaceColor',mountainColor, 'MarkerEdgeColor',mountainColor);
set(markerWa,'MarkerFaceColor',waterColor,'MarkerEdgeColor',waterColor);
axis([0.5 cols-1.5 0.5 rows-1.5])
box off;
set(gca,'xcolor',get(gcf,'color'));
set(gca,'ycolor',get(gcf,'color'));
set(gca,'xtick',[]);
set(gca,'ytick',[]);
set(gca,'Color',[0.85,0.8,0.75]);




